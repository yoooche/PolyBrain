# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

PolyBrain 3.0：桌遊線上商店的 Java 學習專題（多人協作）。目前正在把前端從 JSP/HTML 重構成 `frontend/` 底下的 React SPA，後端維持原樣。

## Git commit 規則

Commit message 一律以 Conventional Commits 類型開頭：`feat:`、`fix:`、`chore:`、`refactor:`、`docs:`、`style:`、`test:`、`build:`、`ci:`、`perf:`。可加 scope，例如 `feat(frontend): 競標詳情頁`。舊 commit（`z`、`kk`、`gogogo`）不符合這個規則，不要模仿。

## 指令

後端（Maven，打包成 `PolyBrain.war`，需部署到 Tomcat，context path 是 `/PolyBrain`）：

```bash
mvn -B package          # CI 跑的就是這個（GitHub Actions，JDK 17；pom 的 compiler target 是 14）
                        # 必須用 JDK 17：Lombok 1.18.28 在 JDK 21 上會編譯失敗（JCTree$JCImport 錯誤）
```

後端沒有任何測試（沒有 `src/test`）。

前端（`frontend/`，Vite + React 19 + TypeScript + Tailwind v4 + shadcn/ui）：

```bash
cd frontend
npm run dev             # http://localhost:5173
npm run typecheck       # tsc -b
npm run build           # 先 tsc -b 再 vite build，型別錯誤會讓 build 失敗
npm run lint            # oxlint
npx shadcn@latest add <component>   # 新增 shadcn 元件到 src/components/ui/
```

前端目前沒有測試框架。

## 後端架構

- 依分層（package-by-layer）組織，不分功能模組，全部在 `com.polybrain` 底下：`controller`（Servlet）、`websocket`（`@ServerEndpoint`）、`service`（介面與沒有介面的 Service）、`service/impl`、`repository`（DAO 介面，或沒有介面的 DAO 類別；類別名稱仍是 `XxxDao`）、`repository/impl`、`model`（Hibernate entity 與 JDBC 物件）、`dto`、`config`、`filter`、`listener`、`util`。
- 資料物件一律以 `TO` 結尾（例如 `MemTO`、`BidEventTO`），不要用 `Vo`。只有類別名稱是 `TO`，欄位與變數名稱（例如 `bidItemVo`）維持原樣，因為 Gson 以欄位名稱當 JSON key，前端依賴這些 key。
- 同一張表有兩組重複的 entity：`GameTableTO`／`TableBookingTO`（前台訂位）與 `AdminGametableTO`／`AdminTablebookingTO`（員工後台「時段管理」，原 `bookingtast` 模組）。員工與會員的登入 Servlet 分別是 `EmpLoginController`、`MemLoginController`。
- macOS 檔案系統不分大小寫，所有類別都在少數幾個 package 裡，新增類別前要確認沒有只差大小寫的同名類別。
- JSP 用 `<%@ page import %>` 直接引用 Java 類別，搬動或改名類別時要一起改 JSP。
- **Transaction 由 Filter 管，不在 Service 裡**：`filter/HibernateFilter` 攔 `/general/*` 和 `*.jsp`，`filter/LoginFilter` 攔 `/loginRequired/*`（未登入回 401，但仍會繼續執行 chain）。兩者都在請求前 `beginTransaction()`、請求後 `commit()`。DAO 透過 `repository/CoreDao.getSession()` 拿 `getCurrentSession()`。因此新的 Servlet 必須掛在這兩個 URL 前綴底下，否則 DAO 會沒有 transaction。
- 實際使用的 Hibernate 設定是 `util/HibernateUtil` + `src/main/resources/hibernate.cfg.xml`（JNDI `java:comp/env/jdbc/polybrain`，連線池定義在 `src/main/webapp/META-INF/context.xml`，MySQL）。**新增 entity 要加進 `hibernate.cfg.xml` 的 `<mapping>`**。`config/AppConfig`（Spring）沒有被任何程式使用，`applicationContext.xml` 也是空的。
- Servlet 大多用 `@WebServlet` 註冊；`WEB-INF/web.xml` 裡另有幾個 XML 註冊，其中 `com.polybrain.controller.loginServlet` 指向不存在的類別（搬移前就是壞的）。
- 很多 Servlet 用單一 URL 加 `message` 參數分派動作，例如 `BiddingController`（`/general/bidding`）以 `req.getParameter("message")` 區分 `selectAllEvent`、`getBiddingItemInfo`、`rangeBarSetting`、`getBiddingTimer`、`getBiddingItemPics` 等，參數以 form-urlencoded 傳入，回傳 Gson 序列化的 JSON。
- 競標即時出價：`websocket/BidOneWebsocket`（`/BidOnePage/{memName}/{bidEventId}`）把出價寫進本機 Redis（`localhost:6379`）的 sorted set，key 是 bidEventId、score 是金額；結標時 `BiddingServiceImpl.createOneOrder` 取最高分建立訂單並寄信。`bidders` 是所有場次共用的 Set，訊息會廣播到所有場次，前端要自己依 `bidEventId` 過濾。
- 登入狀態放在 `HttpSession`（`sessionId`、`memNo`），前端用 `/general/validateMemStatus` 查詢。

## 前端架構（`frontend/`）

- 所有後端呼叫都經過 `src/api/http.ts`：base URL 讀 `VITE_API_BASE`（`.env` 設為 `/PolyBrain`），提供 `http.get/post/postForm/put/delete<T>()` 和 `wsUrl()`；非 2xx 丟 `HttpError`。舊 Servlet 用 `getParameter` 讀值，所以呼叫它們要用 `postForm`。各模組的 API 包在 `src/api/<模組>.ts`，回應型別放在 `src/types/`，欄位對應 Java 的 `model` / `dto` 類別。
- 開發時 `vite.config.ts` 把 `/PolyBrain`（含 WebSocket）proxy 到 `localhost:8080` 的 Tomcat，所以前端程式碼裡不要寫死主機。
- 後端連不上時，頁面會退回 `src/mocks/bidSamples.ts` 的示範資料並顯示提示標籤。
- 路由用 `react-router`，定義在 `src/App.tsx`。即時競標邏輯封裝在 `src/hooks/useBidSocket.ts`。
- `@/*` 別名指向 `src/`（TypeScript 7 已移除 `baseUrl`，只用 `paths`）。這版 shadcn 用 `cn` 套件（`import { cn } from "cn"`），沒有 `src/lib/utils.ts`。

## 舊前端

`src/main/webapp/view/<模組>/` 是舊頁面（JSP + HTML + jQuery，多個 Bootstrap/jQuery 版本混用，且有大量寫死的 `http://localhost:8080`）。搬移到 `frontend/` 時以這些頁面的 JS 作為 API 用法參考，不要再修改它們的樣式。

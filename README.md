# PolyBrain 3.0

A board game online shop built as a team learning project in Java. It covers a storefront, real-time auctions, table booking at the physical store, a forum, and a back office for employees.

The frontend is being migrated from JSP / jQuery pages to a React single-page app in [`frontend/`](frontend/). The backend stays as a Servlet + Hibernate WAR.

## Features

| Area | What it does |
|---|---|
| Members | Register, log in, edit profile |
| Products | Browse, search, product detail, favorites |
| Cart & orders | Shopping cart, checkout, order history |
| Auctions | Live bidding over WebSocket, countdown, buy-it-now, automatic order on close |
| Table booking | Book a game table by date and time slot, email notifications |
| Forum | Posts, replies, reports |
| Back office | Employee login, member / employee management, time-slot management, auction and order management |

## Tech stack

**Backend**
- Java (compiled with JDK 17, target 14), Maven, packaged as `PolyBrain.war`
- Servlet 4 (`javax.*`) + JSP / JSTL, deployed on Tomcat 9
- Hibernate 5.6 on MySQL, connection pool via Tomcat JNDI (HikariCP)
- `javax.websocket` for live bidding and booking updates
- Redis (Jedis) to rank bids per auction
- Gson, Lombok, Spring Mail

**Frontend (`frontend/`)**
- React 19, TypeScript, Vite
- Tailwind CSS v4, shadcn/ui
- React Router

## Project structure

```
src/main/java/com/polybrain/
├── controller/      Servlets
├── websocket/       WebSocket endpoints
├── service/         Business logic (interfaces + impl/)
├── repository/      Data access (interfaces + impl/)
├── model/           Hibernate entities and data objects (XxxTO)
├── dto/
├── config/  filter/  listener/  util/
src/main/webapp/view/   Legacy JSP / HTML pages (being replaced by frontend/)
frontend/               React app
```

Transactions are opened and committed by `filter/HibernateFilter` (`/general/*`, `*.jsp`) and `filter/LoginFilter` (`/loginRequired/*`), so new Servlets must be mapped under one of those prefixes.

## Running locally

### Prerequisites

- JDK 17 (Lombok 1.18.28 does not compile on JDK 21)
- Maven 3.9+
- Tomcat 9
- MySQL with a `polybrain` database (no schema script is included in this repo)
- Redis on `localhost:6379`
- Node.js 20+ for the frontend

### Backend

1. Set the MySQL connection in `src/main/webapp/META-INF/context.xml` (JNDI name `jdbc/polybrain`).
2. Build the WAR:
   ```bash
   mvn -B package
   ```
3. Deploy `target/PolyBrain.war` to Tomcat. The app runs under `http://localhost:8080/PolyBrain`.

### Frontend

```bash
cd frontend
npm install
npm run dev        # http://localhost:5173
```

In development, Vite proxies `/PolyBrain` (including WebSocket) to `localhost:8080`. If the backend is not running, pages fall back to sample data.

Other scripts: `npm run build` (type-checks first), `npm run typecheck`, `npm run lint`.

## Migration status

| Module | React | Legacy pages |
|---|---|---|
| Auctions: list and live bidding | Done | Back-office pages still on JSP |
| Everything else | Not started | JSP / HTML |

Legacy pages are removed module by module once their React version works.

## Known issues

- `WEB-INF/web.xml` registers `com.polybrain.controller.loginServlet`, a class that does not exist.
- `BidOneWebsocket` shares one connection set across all auctions, so every bid is broadcast to every auction page. The React client filters by auction ID.
- The legacy auction page triggers order creation from the browser when the countdown ends, which can create duplicate orders. The React page does not do this; closing an auction should move to a server-side job.
- Two pairs of entities map the same tables (`GameTableTO` / `AdminGametableTO`, `TableBookingTO` / `AdminTablebookingTO`).

## Commit convention

Commit messages start with a [Conventional Commits](https://www.conventionalcommits.org/) type: `feat:`, `fix:`, `refactor:`, `docs:`, `chore:`, and so on.

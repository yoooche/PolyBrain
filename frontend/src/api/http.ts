// 所有對後端的呼叫都走這支，取代舊頁面裡寫死的 http://localhost:8080
const API_BASE = import.meta.env.VITE_API_BASE ?? '/PolyBrain'

type QueryValue = string | number | boolean | null | undefined
export type QueryParams = Record<string, QueryValue>

export class HttpError extends Error {
  readonly status: number
  readonly body: unknown

  constructor(status: number, body: unknown) {
    super(`HTTP ${status}`)
    this.name = 'HttpError'
    this.status = status
    this.body = body
  }

  // LoginFilter 未登入時回 401
  get isUnauthorized(): boolean {
    return this.status === 401
  }
}

interface RequestOptions {
  method?: 'GET' | 'POST' | 'PUT' | 'DELETE'
  params?: QueryParams
  json?: unknown
  form?: Record<string, string>
  headers?: Record<string, string>
}

function buildUrl(path: string, params?: QueryParams): URL {
  const url = new URL(`${API_BASE}${path}`, window.location.origin)
  if (params) {
    Object.entries(params).forEach(([key, value]) => {
      if (value !== undefined && value !== null) url.searchParams.set(key, String(value))
    })
  }
  return url
}

async function parseBody(resp: Response): Promise<unknown> {
  const text = await resp.text()
  if (!text) return null
  try {
    return JSON.parse(text)
  } catch {
    return text
  }
}

async function request<T>(
  path: string,
  { method = 'GET', params, json, form, headers }: RequestOptions = {},
): Promise<T> {
  const init: RequestInit & { headers: Record<string, string> } = {
    method,
    credentials: 'include',
    headers: { ...headers },
  }

  if (json !== undefined) {
    init.headers['Content-Type'] = 'application/json; charset=utf-8'
    init.body = JSON.stringify(json)
  } else if (form !== undefined) {
    // 舊 Servlet 多半用 req.getParameter() 讀值，所以要送 form-urlencoded
    init.body = new URLSearchParams(form)
  }

  const resp = await fetch(buildUrl(path, params), init)
  const body = await parseBody(resp)
  if (!resp.ok) throw new HttpError(resp.status, body)
  // 後端回應沒有執行期驗證，型別由呼叫端宣告
  return body as T
}

export const http = {
  get: <T>(path: string, params?: QueryParams) => request<T>(path, { params }),
  post: <T>(path: string, json?: unknown) => request<T>(path, { method: 'POST', json }),
  postForm: <T>(path: string, form: Record<string, string>) =>
    request<T>(path, { method: 'POST', form }),
  put: <T>(path: string, json?: unknown) => request<T>(path, { method: 'PUT', json }),
  delete: <T>(path: string, params?: QueryParams) =>
    request<T>(path, { method: 'DELETE', params }),
}

// WebSocket 也共用同一個 base，例如 wsUrl('/BidOnePage/louis/1')
export function wsUrl(path: string): string {
  const protocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:'
  return `${protocol}//${window.location.host}${API_BASE}${path}`
}

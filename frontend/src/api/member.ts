import type { LoginStatus } from '@/types/member'
import { http } from './http'

// 對應 feature/mem/controller/ValidateMemStatus
export const memberApi = {
  getLoginStatus: () => http.post<LoginStatus>('/general/validateMemStatus'),
}

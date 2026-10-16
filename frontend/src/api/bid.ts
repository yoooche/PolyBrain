import type { BidEvent, BidItemInfo, BidPriceRange, BidTimer } from '@/types/bid'
import { http } from './http'

// 對應 com.polybrain.controller.BiddingController（@WebServlet("/general/bidding")）
const BIDDING = '/general/bidding'

const query = <T>(message: string, bidEventId?: number) =>
  http.postForm<T>(BIDDING, {
    message,
    ...(bidEventId !== undefined && { bidEventId: String(bidEventId) }),
  })

export const bidApi = {
  listEvents: () => query<BidEvent[]>('selectAllEvent'),
  getItemInfo: (bidEventId: number) => query<BidItemInfo>('getBiddingItemInfo', bidEventId),
  getPriceRange: (bidEventId: number) => query<BidPriceRange>('rangeBarSetting', bidEventId),
  getTimer: (bidEventId: number) => query<BidTimer>('getBiddingTimer', bidEventId),
  // 回傳 base64 字串陣列（後端用 Set 去重，所以順序不固定）
  getPictures: (bidEventId: number) => query<string[]>('getBiddingItemPics', bidEventId),
}

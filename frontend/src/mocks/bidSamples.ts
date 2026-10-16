import type { BidEvent, BidItemInfo, BidPriceRange, BidTimer } from '@/types/bid'

// 後端沒啟動時的示範資料，欄位對應 BidEventVo
export const SAMPLE_EVENTS: BidEvent[] = [
  { bidEventNo: 1, floorPrice: 800, directivePrice: 2400, leastOffers: 50, closeTime: '2026-10-20 21:00:00', bidItemVo: { bidItemName: '卡坦島 25 週年紀念版', gamePublisher: 'KOSMOS', bidItemDescribe: '全新未拆封，含 25 週年限定木製配件。' } },
  { bidEventNo: 2, floorPrice: 1500, directivePrice: 4800, leastOffers: 100, closeTime: '2026-10-21 20:00:00', bidItemVo: { bidItemName: '璀璨寶石 絕版初版', gamePublisher: 'Space Cowboys', bidItemDescribe: '2014 年初版，籌碼完整，盒況九成新。' } },
  { bidEventNo: 3, floorPrice: 600, directivePrice: 1800, leastOffers: 30, closeTime: '2026-10-22 22:30:00', bidItemVo: { bidItemName: '阿瓦隆 簽名版', gamePublisher: 'Indie Boards & Cards', bidItemDescribe: '設計師親筆簽名，附簽名合照。' } },
]

export interface BidDetail {
  info: BidItemInfo
  range: BidPriceRange
  timer: BidTimer
  pictures: string[]
}

export function sampleDetail(bidEventId: number): BidDetail | null {
  const event = SAMPLE_EVENTS.find((e) => e.bidEventNo === bidEventId)
  if (!event) return null
  return {
    info: {
      bidItemName: event.bidItemVo?.bidItemName ?? '',
      gamePublisher: event.bidItemVo?.gamePublisher ?? '',
      bidItemDescribe: event.bidItemVo?.bidItemDescribe ?? '',
      itemClassName: '策略遊戲',
      floorPrice: event.floorPrice,
      directivePrice: event.directivePrice,
    },
    range: { floorPrice: event.floorPrice, directivePrice: event.directivePrice, leastOffers: event.leastOffers },
    timer: { startTime: '2026-10-15 20:00:00', closeTime: event.closeTime },
    pictures: [],
  }
}

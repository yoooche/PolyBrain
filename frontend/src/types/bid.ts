// 對應 feature/bid/vo/BidItemVo、BidEventVo，只列前端用到的欄位
export interface BidItem {
  bidItemNo?: number
  bidItemName: string
  gamePublisher?: string
  bidItemDescribe?: string
}

export interface BidEvent {
  bidEventNo: number
  bidItemNo?: number
  floorPrice: number
  directivePrice: number
  leastOffers: number
  startTime?: string
  closeTime: string
  bidItemVo?: BidItem
}

// BiddingController message=getBiddingItemInfo 的回應
export interface BidItemInfo {
  bidItemName: string
  gamePublisher: string
  bidItemDescribe: string
  itemClassName: string
  floorPrice: number
  directivePrice: number
}

// BiddingController message=rangeBarSetting 的回應
export interface BidPriceRange {
  floorPrice: number
  directivePrice: number
  leastOffers: number
}

// BiddingController message=getBiddingTimer 的回應，格式 yyyy-MM-dd HH:mm:ss
export interface BidTimer {
  startTime: string
  closeTime: string
}

// BidOneWebsocket 收發的一筆出價
export interface BidRecord {
  bidder: string
  biddingRange: number
  bidEventId?: string
}

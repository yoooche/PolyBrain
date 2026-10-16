import { formatPrice } from '@/lib/format'
import type { BidRecord } from '@/types/bid'

interface BidHistoryProps {
  bids: BidRecord[]
  me: string | null
}

export function BidHistory({ bids, me }: BidHistoryProps) {
  if (bids.length === 0) {
    return <p className="py-6 text-center text-sm text-muted-foreground">還沒有人出價，成為第一個吧</p>
  }

  // 最新的出價放最上面
  const latestFirst = [...bids].reverse()

  return (
    <ol className="max-h-72 divide-y overflow-y-auto">
      {latestFirst.map((bid, index) => (
        <li key={bids.length - index} className="flex items-center justify-between py-2 text-sm">
          <span className={bid.bidder === me ? 'font-semibold' : undefined}>
            {bid.bidder}
            {bid.bidder === me && <span className="ml-1 text-muted-foreground">（你）</span>}
          </span>
          <span className="font-mono tabular-nums">{formatPrice(bid.biddingRange)}</span>
        </li>
      ))}
    </ol>
  )
}

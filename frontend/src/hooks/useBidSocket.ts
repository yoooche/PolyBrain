import { useCallback, useEffect, useRef, useState } from 'react'
import { wsUrl } from '@/api/http'
import type { BidRecord } from '@/types/bid'

export type BidSocketStatus = 'idle' | 'connecting' | 'open' | 'closed'

interface ConnectionState {
  key: string | null
  status: BidSocketStatus
  bids: BidRecord[]
}

interface UseBidSocketResult {
  bids: BidRecord[]
  highest: BidRecord | null
  status: BidSocketStatus
  placeBid: (amount: number) => boolean
}

function parseBidRecord(raw: unknown): BidRecord | null {
  if (typeof raw !== 'string') return null
  try {
    const data = JSON.parse(raw) as Record<string, unknown>
    const amount = Number(data.biddingRange)
    if (typeof data.bidder !== 'string' || !Number.isFinite(amount)) return null
    return {
      bidder: data.bidder,
      biddingRange: amount,
      bidEventId: data.bidEventId === undefined ? undefined : String(data.bidEventId),
    }
  } catch {
    return null
  }
}

/**
 * 連到 BidOneWebsocket（/BidOnePage/{memName}/{bidEventId}），取代舊版 bidding.js 的 connect()。
 * memName 為 null（未登入）時不連線。
 */
export function useBidSocket(bidEventId: number, memName: string | null): UseBidSocketResult {
  // 每條連線用 key 區分；換場次或換人時，舊連線的狀態自動作廢，不用在 effect 裡重設
  const connectionKey = memName ? `${bidEventId}:${memName}` : null
  const [connection, setConnection] = useState<ConnectionState>({
    key: null,
    status: 'idle',
    bids: [],
  })
  const socketRef = useRef<WebSocket | null>(null)

  useEffect(() => {
    if (!memName || !connectionKey) return

    const eventKey = String(bidEventId)
    const update = (change: (prev: ConnectionState) => Partial<ConnectionState>) =>
      setConnection((prev) => {
        const base = prev.key === connectionKey ? prev : { key: connectionKey, status: 'connecting' as const, bids: [] }
        return { ...base, ...change(base) }
      })

    const ws = new WebSocket(
      wsUrl(`/BidOnePage/${encodeURIComponent(memName)}/${encodeURIComponent(eventKey)}`),
    )
    socketRef.current = ws

    ws.onopen = () => update(() => ({ status: 'open' }))
    ws.onclose = () => update(() => ({ status: 'closed' }))
    ws.onmessage = (event) => {
      const record = parseBidRecord(event.data)
      // 後端會把訊息廣播給所有場次的連線，只留這一場的
      if (!record || (record.bidEventId !== undefined && record.bidEventId !== eventKey)) return
      update((prev) => ({ bids: [...prev.bids, record] }))
    }

    // 離開頁面或換場次時斷線
    return () => {
      ws.close()
      if (socketRef.current === ws) socketRef.current = null
    }
  }, [bidEventId, memName, connectionKey])

  const placeBid = useCallback(
    (amount: number) => {
      const ws = socketRef.current
      if (!memName || !ws || ws.readyState !== WebSocket.OPEN) return false
      const record: BidRecord = { bidder: memName, biddingRange: amount, bidEventId: String(bidEventId) }
      ws.send(JSON.stringify(record))
      return true
    },
    [bidEventId, memName],
  )

  const isCurrent = connection.key !== null && connection.key === connectionKey
  const bids = isCurrent ? connection.bids : []
  const status: BidSocketStatus = !connectionKey ? 'idle' : isCurrent ? connection.status : 'connecting'

  const highest = bids.reduce<BidRecord | null>(
    (top, bid) => (top === null || bid.biddingRange > top.biddingRange ? bid : top),
    null,
  )

  return { bids, highest, status, placeBid }
}

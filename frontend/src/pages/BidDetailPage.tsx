import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router'
import { ArrowLeft, Gavel, Wifi, WifiOff } from 'lucide-react'
import { bidApi } from '@/api/bid'
import { memberApi } from '@/api/member'
import { BidCountdown } from '@/components/bid/BidCountdown'
import { BidGallery } from '@/components/bid/BidGallery'
import { BidHistory } from '@/components/bid/BidHistory'
import { Badge } from '@/components/ui/badge'
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'
import { Input } from '@/components/ui/input'
import { Separator } from '@/components/ui/separator'
import { Slider } from '@/components/ui/slider'
import { useBidSocket, type BidSocketStatus } from '@/hooks/useBidSocket'
import { useCountdown } from '@/hooks/useCountdown'
import { formatPrice } from '@/lib/format'
import { sampleDetail, type BidDetail } from '@/mocks/bidSamples'

type LoadState =
  | { kind: 'loading' }
  | { kind: 'ready'; detail: BidDetail; source: 'api' | 'sample' }
  | { kind: 'not-found' }

const STATUS_LABEL: Record<BidSocketStatus, string> = {
  idle: '未連線',
  connecting: '連線中',
  open: '即時連線中',
  closed: '連線中斷',
}

async function loadDetail(bidEventId: number): Promise<BidDetail> {
  const [info, range, timer, pictures] = await Promise.all([
    bidApi.getItemInfo(bidEventId),
    bidApi.getPriceRange(bidEventId),
    bidApi.getTimer(bidEventId),
    bidApi.getPictures(bidEventId),
  ])
  return { info, range, timer, pictures }
}

export function BidDetailPage() {
  const bidEventId = Number(useParams().eventId)
  const [state, setState] = useState<LoadState>({ kind: 'loading' })
  const [memName, setMemName] = useState<string | null>(null)

  useEffect(() => {
    let cancelled = false
    loadDetail(bidEventId)
      .then((detail) => !cancelled && setState({ kind: 'ready', detail, source: 'api' }))
      .catch(() => {
        if (cancelled) return
        const sample = sampleDetail(bidEventId)
        setState(sample ? { kind: 'ready', detail: sample, source: 'sample' } : { kind: 'not-found' })
      })
    memberApi
      .getLoginStatus()
      .then((s) => !cancelled && setMemName(s.loginStatus && s.memName ? s.memName : null))
      .catch(() => !cancelled && setMemName(null))
    return () => {
      cancelled = true
    }
  }, [bidEventId])

  if (state.kind === 'loading') {
    return <main className="mx-auto max-w-6xl px-4 py-10 text-muted-foreground">載入中…</main>
  }
  if (state.kind === 'not-found') {
    return (
      <main className="mx-auto max-w-6xl px-4 py-10">
        <p className="mb-4">找不到編號 {Number.isNaN(bidEventId) ? '' : bidEventId} 的競標活動。</p>
        <BackLink />
      </main>
    )
  }

  return <BidDetailView bidEventId={bidEventId} detail={state.detail} source={state.source} memName={memName} />
}

function BackLink() {
  return (
    <Button asChild variant="ghost" size="sm" className="-ml-2">
      <Link to="/">
        <ArrowLeft />
        回競標列表
      </Link>
    </Button>
  )
}

interface BidDetailViewProps {
  bidEventId: number
  detail: BidDetail
  source: 'api' | 'sample'
  memName: string | null
}

function BidDetailView({ bidEventId, detail, source, memName }: BidDetailViewProps) {
  const { info, range, timer, pictures } = detail
  const { bids, highest, status, placeBid } = useBidSocket(bidEventId, memName)
  const countdown = useCountdown(timer.closeTime)
  const ended = countdown?.ended ?? false

  // 有人出價後，下一口至少要比最高價多 leastOffers；沒人出價就從底價開始
  const minBid = highest ? highest.biddingRange + range.leastOffers : range.floorPrice
  const maxBid = range.directivePrice
  const [amount, setAmount] = useState(minBid)
  const effectiveAmount = Math.min(Math.max(amount, minBid), maxBid)
  const canBid = status === 'open' && !ended && minBid <= maxBid

  const bidButtonLabel = (() => {
    if (ended) return '競標已結束'
    if (!memName) return '登入後才能出價'
    if (minBid > maxBid) return '已達直購價上限'
    if (status !== 'open') return STATUS_LABEL[status]
    return `出價 ${formatPrice(effectiveAmount)}`
  })()

  return (
    <main className="mx-auto max-w-6xl px-4 py-8">
      <div className="mb-6 flex items-center justify-between gap-4">
        <BackLink />
        {source === 'sample' && <Badge variant="outline">後端未連線，顯示示範資料</Badge>}
      </div>

      <div className="grid gap-8 lg:grid-cols-2">
        <BidGallery pictures={pictures} alt={info.bidItemName} />

        <div className="flex flex-col gap-6">
          <div>
            <div className="mb-2 flex flex-wrap gap-2">
              <Badge variant="secondary">#{bidEventId}</Badge>
              {info.itemClassName && <Badge variant="outline">{info.itemClassName}</Badge>}
            </div>
            <h1 className="text-3xl font-semibold tracking-tight">{info.bidItemName}</h1>
            {info.gamePublisher && <p className="mt-1 text-muted-foreground">{info.gamePublisher}</p>}
          </div>

          {countdown && (
            <div className="flex flex-col gap-2">
              <span className="text-sm text-muted-foreground">距離結標 · {timer.closeTime}</span>
              <BidCountdown countdown={countdown} />
            </div>
          )}

          <div className="grid grid-cols-3 gap-3 text-sm">
            <PriceStat label="底價" value={range.floorPrice} />
            <PriceStat label="直購價" value={range.directivePrice} />
            <PriceStat label="最低加價" value={range.leastOffers} />
          </div>

          <Card>
            <CardHeader>
              <CardDescription className="flex items-center justify-between">
                目前最高價
                <span className="flex items-center gap-1 text-xs">
                  {status === 'open' ? <Wifi className="size-3.5" /> : <WifiOff className="size-3.5" />}
                  {STATUS_LABEL[status]}
                </span>
              </CardDescription>
              <CardTitle className="text-3xl tabular-nums">
                {highest ? formatPrice(highest.biddingRange) : '尚無出價'}
              </CardTitle>
              {highest && <p className="text-sm text-muted-foreground">由 {highest.bidder} 出價</p>}
            </CardHeader>
            <CardContent className="flex flex-col gap-4">
              <Slider
                value={[effectiveAmount]}
                min={Math.min(minBid, maxBid)}
                max={maxBid}
                step={range.leastOffers}
                disabled={!canBid}
                onValueChange={([value]) => setAmount(value)}
                aria-label="出價金額"
              />
              <div className="flex gap-2">
                <Input
                  type="number"
                  inputMode="numeric"
                  min={minBid}
                  max={maxBid}
                  step={range.leastOffers}
                  value={effectiveAmount}
                  disabled={!canBid}
                  onChange={(e) => setAmount(Number(e.target.value))}
                  className="w-36 tabular-nums"
                  aria-label="出價金額"
                />
                <Button className="flex-1" disabled={!canBid} onClick={() => placeBid(effectiveAmount)}>
                  <Gavel />
                  {bidButtonLabel}
                </Button>
              </div>
              <p className="text-xs text-muted-foreground">
                可出價範圍 {formatPrice(Math.min(minBid, maxBid))} ～ {formatPrice(maxBid)}
              </p>
            </CardContent>
          </Card>
        </div>
      </div>

      <div className="mt-10 grid gap-8 lg:grid-cols-2">
        <section>
          <h2 className="mb-3 text-lg font-semibold">商品描述</h2>
          <p className="whitespace-pre-line leading-relaxed text-muted-foreground">
            {info.bidItemDescribe || '賣家沒有提供描述。'}
          </p>
        </section>
        <section>
          <h2 className="mb-3 text-lg font-semibold">出價紀錄（{bids.length}）</h2>
          <Separator />
          <BidHistory bids={bids} me={memName} />
        </section>
      </div>
    </main>
  )
}

function PriceStat({ label, value }: { label: string; value: number }) {
  return (
    <div className="rounded-lg border bg-background p-3">
      <div className="text-muted-foreground">{label}</div>
      <div className="mt-1 font-semibold tabular-nums">{formatPrice(value)}</div>
    </div>
  )
}

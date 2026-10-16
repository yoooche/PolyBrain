import { useEffect, useState } from 'react'
import { Link } from 'react-router'
import { Clock, Gavel } from 'lucide-react'
import { bidApi } from '@/api/bid'
import { Badge } from '@/components/ui/badge'
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card'
import { Input } from '@/components/ui/input'
import { formatPrice } from '@/lib/format'
import { SAMPLE_EVENTS } from '@/mocks/bidSamples'
import type { BidEvent } from '@/types/bid'

type DataSource = 'loading' | 'api' | 'sample'

export function BidEventsPage() {
  const [events, setEvents] = useState<BidEvent[]>([])
  const [source, setSource] = useState<DataSource>('loading')
  const [keyword, setKeyword] = useState('')

  useEffect(() => {
    bidApi
      .listEvents()
      .then((data) => {
        setEvents(Array.isArray(data) ? data : [])
        setSource('api')
      })
      .catch(() => {
        setEvents(SAMPLE_EVENTS)
        setSource('sample')
      })
  }, [])

  const visible = events.filter((e) => (e.bidItemVo?.bidItemName ?? '').includes(keyword))

  return (
    <main className="mx-auto max-w-6xl px-4 py-10">
      <div className="mb-8 flex flex-col gap-4 md:flex-row md:items-end md:justify-between">
        <div>
          <h1 className="text-3xl font-semibold tracking-tight">競標活動</h1>
          <p className="mt-1 text-muted-foreground">稀有桌遊限時競標，出價即時更新</p>
        </div>
        <div className="flex items-center gap-3">
          {source === 'sample' && <Badge variant="outline">後端未連線，顯示示範資料</Badge>}
          <Input
            placeholder="搜尋商品名稱"
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
            className="w-56"
          />
        </div>
      </div>

      {source === 'loading' ? (
        <p className="text-muted-foreground">載入中…</p>
      ) : (
        <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
          {visible.map((event) => (
            <Card key={event.bidEventNo}>
              <CardHeader>
                <Badge variant="secondary" className="w-fit">
                  #{event.bidEventNo}
                </Badge>
                <CardTitle className="mt-2">{event.bidItemVo?.bidItemName}</CardTitle>
                <CardDescription className="flex items-center gap-1">
                  <Clock className="size-3.5" />
                  結標 {event.closeTime}
                </CardDescription>
              </CardHeader>
              <CardContent className="grid grid-cols-2 gap-2 text-sm">
                <span className="text-muted-foreground">底價</span>
                <span className="text-right font-medium">{formatPrice(event.floorPrice)}</span>
                <span className="text-muted-foreground">直購價</span>
                <span className="text-right font-medium">{formatPrice(event.directivePrice)}</span>
                <span className="text-muted-foreground">最低加價</span>
                <span className="text-right font-medium">{formatPrice(event.leastOffers)}</span>
              </CardContent>
              <CardFooter>
                <Button asChild className="w-full">
                  <Link to={`/bid/${event.bidEventNo}`}>
                    <Gavel />
                    進入競標
                  </Link>
                </Button>
              </CardFooter>
            </Card>
          ))}
        </div>
      )}
    </main>
  )
}

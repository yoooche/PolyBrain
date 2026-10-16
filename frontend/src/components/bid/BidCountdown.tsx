import type { Countdown } from '@/hooks/useCountdown'

const pad = (n: number) => String(n).padStart(2, '0')

export function BidCountdown({ countdown }: { countdown: Countdown }) {
  if (countdown.ended) {
    return <p className="text-lg font-semibold text-destructive">競標已結束</p>
  }

  const units = [
    { value: countdown.days, label: '天' },
    { value: countdown.hours, label: '時' },
    { value: countdown.minutes, label: '分' },
    { value: countdown.seconds, label: '秒' },
  ]

  return (
    <div className="flex items-end gap-3" aria-live="off">
      {units.map(({ value, label }) => (
        <div key={label} className="flex items-baseline gap-1">
          <span className="rounded-md bg-primary px-2 py-1 font-mono text-xl font-semibold tabular-nums text-primary-foreground">
            {pad(value)}
          </span>
          <span className="text-sm text-muted-foreground">{label}</span>
        </div>
      ))}
    </div>
  )
}

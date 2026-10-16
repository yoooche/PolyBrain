import { useEffect, useState } from 'react'

export interface Countdown {
  days: number
  hours: number
  minutes: number
  seconds: number
  ended: boolean
}

// 後端時間格式是 yyyy-MM-dd HH:mm:ss，換成 ISO 才能被每個瀏覽器正確解析
const parseServerTime = (time: string) => Date.parse(time.replace(' ', 'T'))

function diffFrom(target: number, now: number): Countdown {
  const ms = Math.max(0, target - now)
  const totalSeconds = Math.floor(ms / 1000)
  return {
    days: Math.floor(totalSeconds / 86400),
    hours: Math.floor((totalSeconds % 86400) / 3600),
    minutes: Math.floor((totalSeconds % 3600) / 60),
    seconds: totalSeconds % 60,
    ended: ms === 0,
  }
}

// 取代舊版 bidding.js 的 updateTimer()
export function useCountdown(closeTime: string | null): Countdown | null {
  const [now, setNow] = useState(() => Date.now())

  useEffect(() => {
    if (!closeTime) return
    const timer = window.setInterval(() => setNow(Date.now()), 1000)
    return () => window.clearInterval(timer)
  }, [closeTime])

  if (!closeTime) return null
  return diffFrom(parseServerTime(closeTime), now)
}

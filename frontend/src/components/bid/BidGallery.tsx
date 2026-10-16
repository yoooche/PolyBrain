import { useState } from 'react'
import { Dices } from 'lucide-react'
import { cn } from 'cn'

interface BidGalleryProps {
  pictures: string[]
  alt: string
}

// 取代舊版 bidding.js 的 getBiddingItemPics() 與 slideImage()
export function BidGallery({ pictures, alt }: BidGalleryProps) {
  const [active, setActive] = useState(0)
  const current = pictures[active]

  return (
    <div className="flex flex-col gap-3">
      <div className="flex aspect-square items-center justify-center overflow-hidden rounded-xl border bg-muted">
        {current ? (
          <img src={`data:image/jpeg;base64,${current}`} alt={alt} className="size-full object-cover" />
        ) : (
          <div className="flex flex-col items-center gap-2 text-muted-foreground">
            <Dices className="size-12" />
            <span className="text-sm">尚無商品圖片</span>
          </div>
        )}
      </div>
      {pictures.length > 1 && (
        <div className="grid grid-cols-5 gap-2">
          {pictures.map((pic, index) => (
            <button
              key={index}
              type="button"
              onClick={() => setActive(index)}
              aria-label={`第 ${index + 1} 張圖片`}
              className={cn(
                'aspect-square overflow-hidden rounded-md border-2',
                index === active ? 'border-primary' : 'border-transparent opacity-70 hover:opacity-100',
              )}
            >
              <img src={`data:image/jpeg;base64,${pic}`} alt="" className="size-full object-cover" />
            </button>
          ))}
        </div>
      )}
    </div>
  )
}

import { Link } from 'react-router'
import { ShoppingCart, User } from 'lucide-react'
import { Button } from '@/components/ui/button'

const NAV_ITEMS = ['商品', '競標', '桌位預約', '討論區']

// 取代舊專案 55 個檔案各自複製的 <nav>；logo 是淺色圖，所以頁首用深色底
export function AppHeader() {
  return (
    <header className="dark sticky top-0 z-10 border-b bg-background text-foreground">
      <div className="mx-auto flex h-16 max-w-6xl items-center gap-6 px-4">
        <Link to="/">
          <img src="/logo.png" alt="PolyBrain" className="h-8 w-auto" />
        </Link>
        <nav className="hidden gap-1 md:flex">
          {NAV_ITEMS.map((label) => (
            <Button key={label} variant={label === '競標' ? 'secondary' : 'ghost'} size="sm">
              {label}
            </Button>
          ))}
        </nav>
        <div className="ml-auto flex items-center gap-2">
          <Button variant="ghost" size="icon" aria-label="購物車">
            <ShoppingCart />
          </Button>
          <Button variant="outline" size="sm">
            <User />
            登入
          </Button>
        </div>
      </div>
    </header>
  )
}

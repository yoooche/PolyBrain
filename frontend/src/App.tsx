import { BrowserRouter, Route, Routes } from 'react-router'
import { AppHeader } from '@/components/layout/AppHeader'
import { BidDetailPage } from '@/pages/BidDetailPage'
import { BidEventsPage } from '@/pages/BidEventsPage'

function App() {
  return (
    <BrowserRouter>
      <div className="min-h-screen bg-muted/30">
        <AppHeader />
        <Routes>
          <Route path="/" element={<BidEventsPage />} />
          <Route path="/bid/:eventId" element={<BidDetailPage />} />
        </Routes>
      </div>
    </BrowserRouter>
  )
}

export default App

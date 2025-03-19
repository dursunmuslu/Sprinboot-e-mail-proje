import {Component, StrictMode} from 'react'
import { createRoot } from 'react-dom/client'
//import App from './App.jsx'
import "./styles.scss"
import {RouterProvider} from "react-router-dom"
import router from './router'



createRoot(document.getElementById('root')).render(
  <StrictMode>
      <RouterProvider router={router}/>
      {/*<BrowserRouter>
          <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/signup" element={<SignUp />} />
          </Routes>
      </BrowserRouter>*/}
  </StrictMode>
)

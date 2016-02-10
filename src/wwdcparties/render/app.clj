(ns wwdcparties.render.app
  (:require [wwdcparties.render.core :refer :all]
            [wwdcparties.render.twitter :as twitter]))

(def page
  (list
   [:head
    (conj (concat shared-head twitter/app)
          [:title "WWDC Parties for iOS"])]
   [:body [:h1 "WE HAVE AN APP"]]))

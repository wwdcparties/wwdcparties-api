(ns wwdcparties.render.footer
  (:require [hiccup.core :refer [html]]
            [wwdcparties.render.icons :as icons]))

(def footer
  [:footer {:role "contentinfo"}
   [:div.container
    [:div.footer-text
     [:ul.footer-icons
      [:li [:a.tint {:href "webcal://2015.wwdcparties.com/calendar"} icons/calendar "Subscribe"]]
      [:li [:a.tint {:href "http://twitter.com/wwdcparties"} icons/twitter "WWDCParties"]]]

      [:hr]

     [:div.footer-credit
      [:p "Designed and developed by "
      [:a.tint {:href (str "http://pado.name/")} "Geoff Pado"] " and"
      [:a.tint {:href (str "https://twitter.com/mpgstew")} " Emily Stewart"]"."]]]]])

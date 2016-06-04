(ns wwdcparties.render.footer
  (:require [hiccup.core :refer [html]]
            [wwdcparties.render.icons :as icons]))

(def footer
  [:footer {:role "contentinfo" :class "footer"}
    [:div.footer-text
     [:ul.footer-icons
      [:li [:a.footer__link.tint {:href "webcal://2016.wwdcparties.com/calendar"} icons/calendar "Subscribe"]]
      [:li [:a.footer__link.tint {:href "http://twitter.com/wwdcparties"} icons/twitter "WWDCParties"]]]
     [:hr.separator.separator--footer]
     [:div.footer-credit
      [:p "Designed and developed by "
       [:a.footer__link.tint {:href (str "https://twitter.com/mpgstew")} "Emily Stewart"] " and "
       [:a.footer__link.tint {:href (str "http://pado.name/")} "Geoff Pado"] "."]]]])

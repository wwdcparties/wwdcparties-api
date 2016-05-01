(ns wwdcparties.render.nav
  (:require [hiccup.core :refer [html]]
            [wwdcparties.render.icons :as icons]))

(def header
  [:header {:role "header" :class "header header--global" }
   [:nav {:role "navigation" :class "nav nav--global"}
    [:ul
     [:li.site-title [:a {:href "/"} "WWDC Parties " [:span "2016"]]]]]])

(ns wwdcparties.render.sponsor)

(def logo {:src "img/UserTesting-Grayscale.png"
           :alt "Logo for UserTesting.com"})
(def url "http://info.usertesting.com/MobileTesting.html?utm_source=wwdcparties&utm_medium=referral&utm_campaign=wwdcparties")

(def advert
  [:div.site-sponsor
   [:a {:href url}
    [:span.pretext "Sponsored by:"]
    [:br]
    [:div.alpha.logo
     [:img logo]]
    [:div.sponsor-copy.omega
     [:p "Raise a drink to 5-star reviews. See and hear videos of "
      [:strong "real people"]
      " using your released (or unreleased!) app."]]]])

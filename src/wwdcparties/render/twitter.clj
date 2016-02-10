(ns wwdcparties.render.twitter
  (:require [wwdcparties.render.core :refer :all]))

(defpope card [party]
  (list [:meta {:name "twitter:card" :content "summary"}]
        [:meta {:name "twitter:site" :content "@wwdcparties"}]
        [:meta {:name "twitter:title" :content (:name party)}]
        [:meta {:name "twitter:description" :content (:excerpt party)}]
        [:meta {:name "twitter:app:country" :content "US"}]
        [:meta {:name "twitter:app:name:iphone" :content "WWDC Parties"}]
        [:meta {:name "twitter:app:id:iphone" :content "956296991"}]
        [:meta {:name "twitter:app:url:iphone" :content (str "wwdcparties://parties/" (:slug party))}]
        [:meta {:name "twitter:app:name:ipad" :content "WWDC Parties"}]
        [:meta {:name "twitter:app:id:ipad" :content "956296991"}]
        [:meta {:name "twitter:app:url:ipad" :content (str "wwdcparties://parties/" (:slug party))}]))

(def app
  (list
   [:meta {:name "twitter:card" :content "app"}]
   [:meta {:name "twitter:site" :content "@wwdcparties"}]
   [:meta {:name "twitter:description" :content "View the latest parties at Apple's developer conference."}]
   [:meta {:name "twitter:app:name:iphone" :content "WWDC Parties"}]
   [:meta {:name "twitter:app:id:iphone" :content "956296991"}]
   [:meta {:name "twitter:app:name:ipad" :content "WWDC Parties"}]
   [:meta {:name "twitter:app:id:ipad" :content "956296991"}]))

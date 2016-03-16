(ns wwdcparties.render.twitter
  (:require [wwdcparties.render.core :refer :all]))

(defpope card [party]
  (list [:meta {:name "twitter:card" :content "summary"}]
        [:meta {:name "twitter:site" :content "@wwdcparties"}]
        [:meta {:name "twitter:title" :content (:name party)}]
        [:meta {:name "twitter:description" :content (:excerpt party)}]))


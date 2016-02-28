(ns wwdcparties.routes.home
  (:require [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [moved-permanently not-found]]
            [clojure.java.io :as io]
            [wwdcparties.render.index :as index]
            [hiccup.page :refer [html5]]
            [wwdcparties.api.db :as db]
            [wwdcparties.render.info :as info]))

(defn index-page []
  (html5 (index/page (sort-by :start_time (db/parties)))))

(defn info-page [slug]
  (let [party (db/parties slug)]
    (if (empty? party)
      (not-found)
      (html5 (info/page party)))))

(defroutes home-routes
  (GET "/" [] (moved-permanently "/parties/"))
  (GET "/parties/" [] (index-page))
  (GET "/parties/:slug" [slug] (info-page slug)))

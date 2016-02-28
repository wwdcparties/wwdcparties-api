(ns wwdcparties.routes.home
  (:require [clojure.java.io :as io]
            [compojure.core :refer [defroutes GET]]
            [hiccup.page :refer [html5]]
            [ring.util.http-response :refer [moved-permanently not-found]]
            [wwdcparties.api.db :as db]
            [wwdcparties.render.index :as index]
            [wwdcparties.render.info :as info]
            [wwdcparties.render.submit :as submit]))

(defn index-page []
  (html5 (index/page (sort-by :start_time (db/parties)))))

(defn info-page [slug]
  (let [party (db/parties slug)]
    (if (empty? party)
      (not-found)
      (html5 (info/page party)))))

(defn submit-page []
  (html5 (submit/page nil)))

(defroutes home-routes
  (GET "/" [] (moved-permanently "/parties/"))
  (GET "/parties/" [] (index-page))
  (GET "/parties/new" [] (submit-page))
  (GET "/parties/:slug" [slug] (info-page slug)))

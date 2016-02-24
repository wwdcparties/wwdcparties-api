(ns wwdcparties.routes.home
  (:require [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io]
            [wwdcparties.render.index :as index]
            [hiccup.page :refer [html5]]
            [wwdcparties.api.db :as db]
            [wwdcparties.model.party :as party]
            [wwdcparties.render.info :as info]))

(defn index-page []
  (html5 (index/page (sort-by :start_time (db/parties)))))

(defn info-page [slug]
  (html5 (info/page (party/from-json (db/parties slug)))))

(defroutes home-routes
  (GET "/" [] (index-page))
  (GET "/parties/:slug" [slug] (info-page slug)))

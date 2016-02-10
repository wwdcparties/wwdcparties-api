(ns wwdcparties.routes.home
  (:require [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io]
            [wwdcparties.render.index :as index]
            [hiccup.page :refer [html5]]
            [wwdcparties.api.db :as db]
            [wwdcparties.model.party :as party]))

(defn index-page []
  (html5 (index/page (sort-by :start_time (map party/from-json (db/parties))))))

(defroutes home-routes
  (GET "/" [] (index-page)))

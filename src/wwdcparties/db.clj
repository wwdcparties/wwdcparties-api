(ns wwdcparties.db
  (:require [environ.core :refer [env]]
            [com.ashafa.clutch :as clutch]
            [wwdcparties.party :as party]))

(def admin-db (clutch/get-database (env :wwdc-parties-admin-db)))
(def db (clutch/get-database (env :wwdc-parties-db)))

(defn admin [username]
  (:value (first (clutch/get-view admin-db "users" "all" {:key username}))))

(defn get-all-parties []
  (map party/sanitized (map :value
    (clutch/get-view db "parties" "list"))))

(defn get-party-by-slug [slug]
  (party/sanitized
   (first (map :value 
               (clutch/get-view
                db "parties" "list" {:key slug})))))

(defn add-party [party]
  (clutch/put-document db (party/sanitized party)))

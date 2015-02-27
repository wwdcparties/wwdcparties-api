(ns wwdcparties.db
  (:require [environ.core :refer [env]]
            [com.ashafa.clutch :as clutch]
            [wwdcparties.party :as party]
            [wwdcparties.admin :as admin]))

(def db
  (clutch/get-database (env :wwdc-parties-db)))

(def auth-view
  (partial clutch/get-view db "admins" "auth"))
(def parties-view
  (partial clutch/get-view db "parties" "list"))

(defn parties
  ([] 
   (map party/sanitized 
        (map :value (parties-view))))
  ([slug] 
   (-> (parties-view {:key slug}) 
       first :value party/sanitized)))

(defn auth []
  (let [users (auth-view)] (zipmap (map :key users) (map admin/from-db users))))

(defn users
  ([]
   (map admin/from-db (auth-view)))
  ([username] 
   (-> (auth-view {:key username})
       first admin/from-db)))

(defn add-party [party]
  (clutch/put-document db (party/sanitized party)))
  

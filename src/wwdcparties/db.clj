(ns wwdcparties.db
  (:require [environ.core :refer [env]]
            [com.ashafa.clutch :as clutch]
            [wwdcparties.party :as party]
            [wwdcparties.admin :as admin]))

(def db
  (clutch/get-database (env :wwdc-parties-db)))

(defn parties
  ([] 
   (map party/sanitized 
        (map :value 
             (clutch/get-view
              db "parties" "list"))))
  ([slug] 
   (-> (clutch/get-view db "parties" "list" {:key slug}) 
       first :value party/sanitized)))

(defn users
  ([]
   (map admin/from-db (clutch/get-view db "admins" "auth")))
  ([username] 
   (-> (clutch/get-view db "admins" "auth" {:key username})
       first admin/from-db)))

(defn add-party [party]
  (clutch/put-document db (party/sanitized party)))
  

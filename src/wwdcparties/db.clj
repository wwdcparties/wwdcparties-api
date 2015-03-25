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
(def submitted-view
  (partial clutch/get-view db "parties" "submitted"))
(def edits-view
  (partial clutch/get-view db "edits" "list"))

(defn parties
  ([] 
   (map party/from-json
        (map :value (parties-view))))
  ([slug] 
   (-> (parties-view {:key slug}) 
       first :value party/from-json)))

(defn submitted
  ([] 
   (map party/from-json
        (map :value (submitted-view))))
  ([slug] 
   (-> (submitted-view {:key slug}) 
       first :value party/from-json)))

(defn edits
  ([] (let [edits-map (edits-view {:group_level 1})]
        (zipmap (map :key edits-map)
                (map :value edits-map))))
  ([slug] (let [edits-map 
                (first (edits-view {:group_level 1
                                    :key slug}))]
            (:value edits-map)))
  ([slug id] (clutch/get-document db id)))

(defn auth []
  (let [users (auth-view)] (zipmap (map :key users) (map admin/from-db users))))

(defn users
  ([]
   (map admin/from-db (auth-view)))
  ([username] 
   (-> (auth-view {:key username})
       first admin/from-db)))

(defn add-party [party]
  (clutch/put-document db (party/approved (party/from-json party) false)))
  
(defn approve-party [slug]
  (let [party (:value (first (submitted-view {:key slug})))]
    (clutch/put-document
     db (party/approved party))))

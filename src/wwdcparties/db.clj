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

(defn -approved [party approved]
  (= (:approved party) approved))

(defn approved? 
  ([party] (-approved party true))
  ([party approved] (-approved party false)))

(defn parties
  ([] (filter approved? 
              (map party/from-json
                   (map :value (parties-view)))))
  ([slug] 
   (-> (parties-view {:key slug}) 
       first :value party/from-json)))

(defn submitted [] 
  (filter #(approved? % false) 
          (map party/from-json
               (map :value (submitted-view)))))

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

; HERE BE DRAGONS
(defn after [slug]
  (let [found (loop [slug slug
                     remaining (sort-by :start_time (parties))]
                (let [party? (first remaining)]
                  (if (or (= slug (:slug party?))
                          (nil? remaining))
                    {:slug (:slug (fnext remaining))
                     :name (:name (fnext remaining))}
                    (recur slug (next remaining)))))]
    (if (nil? found) nil found)))

(defn before [slug]
  (let [found (loop [slug slug
                     remaining (sort-by :start_time (parties))]
                (let [party? (fnext remaining)
                      before (first remaining)]
                  (if (= slug (:slug party?))
                    {:slug (:slug before)
                     :name (:name before)}
                    (if (nil? (next remaining))
                      nil
                      (recur slug (next remaining))))))]
    (if (nil? found) nil found)))

(defn with-links [party]
  (assoc party 
    :next (after (:slug party))
    :prev (before (:slug party))))

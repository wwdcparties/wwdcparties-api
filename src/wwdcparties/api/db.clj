(ns wwdcparties.api.db
  (:require [environ.core :refer [env]]
            [com.ashafa.clutch :as clutch]
            [wwdcparties.api.party :as party]
            [wwdcparties.api.admin :as admin]))

(defn db
  "Gets the WWDC Parties database." []
  (clutch/get-database (env :wwdc-parties-db)))

(defmacro -view [design view & args]
  `(let [view# (partial clutch/get-view 
                        (clutch/get-database (env :wwdc-parties-db))
                        ~design ~view)]
     (apply view# ~@args)))

(defn auth-view
  "Returns a view for authenticating admins." [& args]
  (-view "admins" "auth" args))
(defn parties-view
  "Returns a view for listing parties." [& args]
  (-view "parties" "list" args))
(defn submitted-view
  "Returns a view for listing party submissions" [& args]
  (-view "parties" "submitted" args))

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

(defn auth []
  (let [users (auth-view)] 
    (zipmap (map :key users) (map admin/from-db users))))

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
                  (if (= slug (:slug party?))
                    (if (nil? (next remaining))
                      nil
                      {:slug (:slug (fnext remaining))
                       :name (:name (fnext remaining))})
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

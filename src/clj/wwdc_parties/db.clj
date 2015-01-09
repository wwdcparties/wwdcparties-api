(ns wwdc-parties.db
  (:require [environ.core :refer [env]]
            [com.ashafa.clutch :as clutch]))

(def db
  (clutch/get-database (env :wwdc-parties-db)))

(defn get-all-parties []
  (map :value
    (clutch/get-view db "parties" "list")))

(defn add-party [party]
	(clutch/put-document db party))

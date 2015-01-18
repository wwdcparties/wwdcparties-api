(ns wwdcparties.db
  (:require [environ.core :refer [env]]
            [com.ashafa.clutch :as clutch]
            [wwdcparties.party :as party]))

(def db
  (clutch/get-database (env :wwdc-parties-db)))

(defn get-all-parties []
  (map party/sanitized (map :value
    (clutch/get-view db "parties" "list"))))

(defn get-party-by-slug [slug]
	(party/sanitized (first
		(map :value (clutch/get-view db
			"parties" "list" {:key slug})))))

(defn add-party [party]
	(clutch/put-document db (party/sanitized party)))
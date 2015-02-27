(ns wwdcparties.db
  (:require [environ.core :refer [env]]
            [com.ashafa.clutch :as clutch]
            [crypto.password.bcrypt :as password]
            [wwdcparties.party :as party]))

(def admin-db (clutch/get-database (env :wwdc-parties-admin-db)))
(def db (clutch/get-database (env :wwdc-parties-db)))

(defn get-admin [username]
  (:value (first (clutch/get-view admin-db "users" "all" {:key username}))))

(defn validate-admin [username password]
  (let [hash (:password (get-admin username))]
    (if (nil? hash) false
      (password/check password hash))))

(defn get-all-parties []
  (map party/sanitized (map :value
    (clutch/get-view db "parties" "list"))))

(defn get-party-by-slug [slug]
	(party/sanitized (first
		(map :value (clutch/get-view db
			"parties" "list" {:key slug})))))

(defn add-party [party]
	(clutch/put-document db (party/sanitized party)))

(ns wwdcparties.admin
  (:require [wwdcparties.db :as db]
            [crypto.password.bcrypt :as password]))

(defn validate [username password]
  (let [hash (:password (db/admin username))]
    (if (nil? hash) false
      (password/check password hash))))

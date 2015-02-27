(ns wwdcparties.admin)

(defn from-db [map]
  {:username (:key map)
   :password (:value map)})

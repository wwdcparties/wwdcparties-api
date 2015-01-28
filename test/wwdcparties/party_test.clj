(ns wwdcparties.party-test
  (:require [clojure.test :refer :all]
            [wwdcparties.party :refer :all]))

(def unsanitary {:name "Test Party" :approved true})

(deftest sanitization
  (testing "Sanitization"
    (is (= (sanitized unsanitary) {:name "Test Party"}))))

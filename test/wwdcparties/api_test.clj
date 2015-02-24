(ns wwdcparties.api-test
  (:require [clojure.test :refer :all]
            [wwdcparties.api :refer :all]))

(deftest non-user
  (testing "Users that don't exist."
    (is (log-in "dead-user" "password"))))

(ns wwdcparties.api.submit-test
  (:require [clojure.test :refer :all]
            [wwdcparties.api.submit :refer :all]))

(deftest test-types
  (testing "single type"
    (is (= (form->types "foo") ["foo"])))
  (testing "multiple types"
    (is (= (form->types ["foo" "bar"]) ["foo" "bar"])))
  (testing "no types"
    (is (= (form->types nil) []))))

(deftest test-meta
  (testing "single key"
    (is (= (form->meta "foo") {"foo" true})))
  (testing "multiple keys"
    (is (= (form->meta ["foo" "bar"]) {"foo" true "bar" true})))
  (testing "no keys"
    (is (= (form->meta nil) {}))))

(deftest test-days
  (testing "single day"
    (is (= (form->days "15") [15])))
  (testing "multiple days"
    (is (= (form->days ["12" "13"]) [12 13])))
  (testing "unsorted days"
    (is (= (form->days ["17" "16"]) [16 17])))
  (testing "no days"
    (is (= (form->days nil) nil))))

(deftest test-date
  (testing "date parsing"
    (is (= (form->date 13 "10:00") 1465837200))))

(def sample-submission 
  {:choice_party_name "Test Party"
   :choice_venue "Test Venue"
   :description "<div>Test&nbsp;<strong>party</strong>, please&nbsp;<em>ignore</em>.</div>"
   :day "13"
   :meta ["21+" "booze"]
   :choice_time_end "17:00"
   :choice_time_start "10:00"
   :party-type "outdoors"})

(deftest test-party
  (let [party (form->party sample-submission)]
    (testing "description"
      (is (= (:description party) "<div>Test&nbsp;<strong>party</strong>, please&nbsp;<em>ignore</em>.</div>")))
    (testing "end time"
      (is (= (:end_time party) 1465862400)))
    (testing "meta"
      (is (= (:meta party) {"21+" true "booze" true})))
    (testing "name"
      (is (= (:name party) "Test Party")))
    (testing "slug"
      (is (= (:slug party) "test-party")))
    (testing "start time"
      (is (= (:start_time party) 1465837200)))
    (testing "types"
      (is (= (:types party) ["outdoors"])))))

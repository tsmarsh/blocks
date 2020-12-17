(ns todoschema.validator-test
  (:require [clojure.test :refer :all]
            [todoschema.validator :refer :all]
            [clojure.spec.alpha :as s]
            [cheshire.core :as c]))

(deftest test-app
  (testing "good"
    (let [json (slurp "./test-resources/good.json")
          good (c/parse-string json keyword)]
      (is (s/valid? :todo/todo good)) ))

  (testing "bad"
    (let [json (slurp "./test-resources/bad.json")
          bad (c/parse-string json keyword)]
      (is (= 2 (count (:clojure.spec.alpha/problems (s/explain-data :todo/todo bad))))))))
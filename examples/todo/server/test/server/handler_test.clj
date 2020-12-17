(ns server.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [server.handler :refer :all]
            [cheshire.core :as c]))

(deftest validation
  (testing "validation"
    (let [body (slurp "../data/good.json")
          request (mock/request :post "/" body)
          req2 (assoc-in request [:headers "content-type"]  "application/json")
          response (app req2)]
      (is (= (:status response) 200))
      (is (= (c/parse-string body) (c/parse-string (:body response))))))

  (testing "failure"
    (let [body (slurp "../data/bad.json")
          request (mock/request :post "/" body)
          req2 (assoc-in request [:headers "content-type"]  "application/json")
          response (app req2)]
      (is (= (:status response) 400))
      (let [rbody (:body response)
            rjson (c/parse-string rbody keyword)
            problems (:clojure.spec.alpha/problems rjson)]
        (is (= 2 (count problems)))))))

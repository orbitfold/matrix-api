(ns core.matrix.test-double-array
  (:use clojure.test)
  (:use core.matrix)
  (:require [core.matrix.operators :as op])
  (:require [core.matrix.compliance-tester])
  (:require core.matrix.impl.double-array))

(deftest test-create
  (testing "making a double array"
    (let [da (matrix :double-array [1 2])]
      (is (= [1.0 2.0] (seq da)))
      (is (= [1.0 2.0] (eseq da)))
      (is (= (class (double-array [1])) (class da)))))
  (testing "coercion from persistent vector"
    (let [da (matrix :double-array [1 2])]
      (is (= [2.0 4.0] (seq (coerce da [2 4]))))
      (is (= (class da) (class (coerce da [2 4])))))))

(deftest test-functional-ops
  (testing "mapping"
    (let [da (matrix :double-array [1 2])]
      (is (= [2.0 3.0] (seq (emap inc da))))
      (emap! inc da) 
      (is (= [2.0 3.0] (eseq da))))))

(deftest test-assign
  (testing "assign from a persistent vector"
    (let [da (double-array [1 2])]
      (assign! da [2 3]) 
      (is (= [2.0 3.0] (seq da))))))

(deftest test-equals
  (testing "equality with persistent vector"
    (let [da (double-array [1 2])] 
      (is (= [1.0 2.0] (to-nested-vectors da)))
      (is (equals [1.0 2.0] da))
      (is (equals [1 2] da))
      (is (equals da [1.0 2.0])))))


(deftest test-maths-ops
  (testing "basic ops"
    (let [da (double-array [1.2 2.3])]
      (println (seq (floor da))) 
      (is (equals [1.0 2.0] (floor da))))))


(deftest compliance-test
  (core.matrix.compliance-tester/compliance-test (double-array [0.23]))) 

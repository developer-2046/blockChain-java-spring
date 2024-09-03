package com.example.blockChain.Models;
import com.example.blockChain.Utils.HashUtil;
import com.example.blockChain.*;
import java.util.*;

public class MerkleTree {
     public static String getMerkleRoot(List<Transaction> transactions) {
            int count = transactions.size();
            List<String> previousTreeLayer = new ArrayList<>();
            for (Transaction transaction : transactions) {
                previousTreeLayer.add(transaction.toString());
            }

                    List<String> treeLayer = previousTreeLayer;
   while (count > 1) {
            treeLayer = new ArrayList<>();
            for (int i = 1; i < previousTreeLayer.size(); i += 2) {
                treeLayer.add(HashUtil.applySha256(previousTreeLayer.get(i - 1) + previousTreeLayer.get(i)));
            }
            count = treeLayer.size();
            previousTreeLayer = treeLayer;
        }
                return (treeLayer.size() == 1) ? treeLayer.get(0) : "";
}

}

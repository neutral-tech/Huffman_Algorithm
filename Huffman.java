import java.util.*;
public class Huffman {
    HashMap<Character,String > encoder; //Table for encoding messages
    HashMap<String,Character> decoder; //Table for decoding messages
    //Node definition
    private class Node implements Comparable<Node>{
        int cost;
        Character data;
        Node left;
        Node right;
        public Node(Character data,int cost){
            this.data=data;
            this.cost=cost;
            this.left=null;
            this.right=null;
        }
        @Override
        public int compareTo(Node next) {
            return this.cost-next.cost;
        }
    }
    //Method for encoding the message
    public String encode(String feeder){
        int i=0;
        char temp;
        String ans="";
        HashMap<Character,Integer> fmap=new HashMap<>();
        while(i<feeder.length()){
            temp=feeder.charAt(i);
            if(fmap.containsKey(temp)){
                fmap.put(temp,fmap.get(temp)+1);
            }
            else fmap.put(temp,1);
            i++;
        }
        PriorityQueue<Node> minHeap=new PriorityQueue<>();
        Set<Map.Entry<Character,Integer>> set=fmap.entrySet();
        for(Map.Entry<Character,Integer> entry: set){
            Node node=new Node(entry.getKey(),entry.getValue());
            minHeap.add(node);
        }
        while(minHeap.size()!=1){
            Node first=minHeap.remove();
            Node second=minHeap.remove();
            Node newNode=new Node('\0',first.cost+second.cost);
            newNode.left=first;
            newNode.right=second;
            minHeap.add(newNode);
        }
        Node root=minHeap.remove();
        this.encoder=new HashMap<>();
        this.decoder=new HashMap<>();
        filltable(root,"");
        i=0;
        while(i<feeder.length()){
            ans+= encoder.get(feeder.charAt(i));
            i++;
        }
        return ans;
    }
    public String decode(String code){
        String key="";
        String ans="";
        int i=0;
        while(i<code.length()){
            key+=code.charAt(i);
            if(decoder.containsKey(key)){
                ans+=decoder.get(key);
                key="";
            }
            i++;
        }
        return ans;
    }
    private void filltable(Node root,String str){
        if(root==null) return;
        else if(root.left==null && root.right==null){
            this.encoder.put(root.data,str);
            this.decoder.put(str,root.data);
        }
        filltable(root.left,str+"0");
        filltable(root.right,str+"1");
    }
}


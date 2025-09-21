public class FNEntry<T> {
        private T dataObject ;
        SLL<T> list;
        private String status ;
        FNEntry(){

            this.dataObject= null;
            this.status="E";
        }
        FNEntry(T dataObject , String status){
            list = new SLL<>();
            list.addToHead(dataObject); // put a linked list in the table
            this.dataObject= dataObject;
            this.status=status;
        }
        public SLL getList(){
            return list;
        }

        public String getStatus(){
            return status;
        }
        @Override
        public String toString(){

            return null;
        }
        public T getData(){
            return dataObject;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }


}

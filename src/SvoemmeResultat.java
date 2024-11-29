public class SvoemmeResultat {
        private String disciplin;
        private double tid;
        private String dato;

        public SvoemmeResultat(String Disciplin, double tid, String dato)
        {
            this.disciplin = Disciplin;
            this.tid = tid;
            this.dato = dato;
        }

        public String getDiscipline()
        {
            return disciplin;
        }

        public double getTime()
        {
            return tid;
        }


        @Override
        public String toString()
        {
            return "Disciplin: " + disciplin + ", Tid: " + tid + " sek, Dato: " + dato;
        }
}

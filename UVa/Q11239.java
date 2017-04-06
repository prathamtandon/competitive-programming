import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Q11239 {
    public static void main(String[] args) throws IOException {
        ProjectFair fair;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while(!(line = reader.readLine()).startsWith("0")) {
            fair = new ProjectFair();
            String project = "";
            while(!line.startsWith("1")) {
                if(line.toUpperCase().equals(line)) {
                    project = line.trim();
                    fair.addProject(project);
                } else {
                    fair.signupStudent(project, line.trim());
                }
                line = reader.readLine();
            }
            fair.printSummary();
        }
    }

    private static class ProjectFair {
        private HashMap<String, TreeSet<String>> projectToStudents;
        private HashMap<String, TreeSet<String>> studentToProjects;

        ProjectFair() {
            projectToStudents = new HashMap<>();
            studentToProjects = new HashMap<>();
        }

        void addProject(String project) {
            projectToStudents.put(project, new TreeSet<>());
        }

        void signupStudent(String project, String student) {
            projectToStudents.get(project).add(student);
            if(!studentToProjects.containsKey(student))
                studentToProjects.put(student, new TreeSet<>());
            studentToProjects.get(student).add(project);
        }

        void removeDuplicateStudents() {
            //noinspection Convert2streamapi
            for(String student: studentToProjects.keySet()) {
                if(studentToProjects.get(student).size() > 1)
                    removeStudentFromProjects(student, studentToProjects.get(student));
            }
        }

        void removeStudentFromProjects(String student, TreeSet<String> projects) {
            for(String project: projects) {
                projectToStudents.get(project).remove(student);
            }
        }

        void printSummary() {
            removeDuplicateStudents();
            List<Map.Entry<String, TreeSet<String>>> list = new ArrayList<>(projectToStudents.entrySet());
            Collections.sort(list, new ValueThenKeyComparator());
            for(Map.Entry<String, TreeSet<String>> entry: list)
                System.out.println(entry.getKey() + " " + entry.getValue().size());
        }

        private static class ValueThenKeyComparator implements Comparator<Map.Entry<String, TreeSet<String>>> {
            public int compare(Map.Entry<String, TreeSet<String>> a, Map.Entry<String, TreeSet<String>> b) {
                int cmp1 = b.getValue().size() - a.getValue().size();
                if(cmp1 != 0)
                    return cmp1;
                else
                    return a.getKey().compareTo(b.getKey());
            }
        }
    }
}
# student-management-system
v1
- All basic requirements for the project are done; Requirements are: Create, read, update, delete, and list, along with sorting and searching

v2
- Massive UI overhaul; Used Flatlaf
- Fixed getting data for the gender field when editing data
- Fixed programs retaining students under them when their code is edited
- Fixed colleges retaining programs under them when their code is edited
- Can now delete programs with students; when deleting programs with students still under them, students will be deleted, too
- Can now delete colleges with programs; when deleting colleges with programs in them, programs will be deleted, too, along with the students under them
- Deleted ManagementToolbar.java and unused custom Exceptions
- Program name can now be seen when adding or editing student data
- Added javadocs for classes and methods
- Added Apache license in accordance with Flatlaf that is under the Apache license
- Added icons downloaded from svgrepo.com; Icons are under CC0 license
- Added proper recognition for AutoCompletion.java by Thomas Bierhance; Article can be found in http://www.orbital-computer.de/JComboBox/

v3
- Fixed setting Programs and College Codes when editing data on the DataMap
- Can now delete multiple rows of data for Students, Programs, and Colleges
- Can now edit multiple rows of data for Students and Programs. Disabled for Colleges
- Can now search by one column
- Will now recognize input on text fields with only whitespace characters as empty input.
- Optimized constructors for DataInput files.
- Optimized catch clauses for custom Exceptions
- Added more javadocs

Transferred previous versions to another branch. The main branch will only have the latest version. <br>
v3 is the latest version for now.<br>

Repository for a school activity on creating a student management system

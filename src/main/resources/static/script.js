const API = "http://localhost:8080/api";


// Set today's date when the page loads
function setTodayDate() {

    const today = new Date();

    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, "0");
    const day = String(today.getDate()).padStart(2, "0");

    document.getElementById("attendanceDate").value =
        `${year}-${month}-${day}`;
}


// Load students
async function loadStudents() {

    try {

        const response = await fetch(`${API}/students`);

        if (!response.ok) {
            throw new Error("Unable to load students");
        }

        const students = await response.json();

        const studentList = document.getElementById("studentList");

        studentList.innerHTML = "";

        if (students.length === 0) {

            studentList.innerHTML =
                "<p>No students found.</p>";

            return;
        }


        students.forEach(student => {

            studentList.innerHTML += `
                <div class="student-row">

                    <span>
                        ${student.name} (${student.rollNumber})
                    </span>

                    <select id="status-${student.id}">

                        <option value="PRESENT">
                            Present
                        </option>

                        <option value="ABSENT">
                            Absent
                        </option>

                        <option value="ON_LEAVE">
                            On Leave
                        </option>

                    </select>

                </div>
            `;

        });


        document.getElementById("markAttendanceBtn").style.display =
            "block";

        document.getElementById("attendanceMessage").textContent =
            "Students loaded successfully.";

    } catch (error) {

        alert("Error loading students: " + error.message);

    }
}


// Mark attendance
async function markAttendance() {

    const subjectId =
        document.getElementById("subjectId").value;

    const date =
        document.getElementById("attendanceDate").value;


    if (!subjectId || !date) {

        alert("Please enter subject ID and attendance date.");

        return;
    }


    try {

        // Get the students first
        const response =
            await fetch(`${API}/students`);

        if (!response.ok) {
            throw new Error("Unable to load students");
        }

        const students =
            await response.json();


        // Prepare attendance data
        const attendanceData = students.map(student => {

            const statusElement =
                document.getElementById(`status-${student.id}`);

            return {
                studentId: student.id,
                status: statusElement.value
            };

        });


        // Send attendance to backend
        const markResponse = await fetch(
            `${API}/attendance/mark/${subjectId}?date=${date}`,
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(attendanceData)
            }
        );


        if (!markResponse.ok) {
            throw new Error("Unable to mark attendance");
        }


        document.getElementById("attendanceMessage").textContent =
            "Attendance marked successfully!";


        // Refresh the records and dashboard
        loadAttendanceRecords();
        loadDashboard();

    } catch (error) {

        alert("Error marking attendance: " + error.message);

    }
}


// Load attendance records
async function loadAttendanceRecords() {

    const subjectId =
        document.getElementById("subjectId").value;


    try {

        const response = await fetch(
            `${API}/attendance/subject/${subjectId}`
        );


        if (!response.ok) {
            throw new Error("Unable to load attendance records");
        }


        const records =
            await response.json();


        const table =
            document.getElementById("attendanceTable");


        table.innerHTML = "";


        if (records.length === 0) {

            table.innerHTML = `
                <tr>
                    <td colspan="5" class="empty-message">
                        No attendance records found.
                    </td>
                </tr>
            `;

            return;
        }


        records.forEach(record => {

            let statusClass = "status-leave";
            let statusText = "On Leave";


            if (record.status === "PRESENT") {

                statusClass = "status-present";
                statusText = "Present";

            } else if (record.status === "ABSENT") {

                statusClass = "status-absent";
                statusText = "Absent";

            }


            table.innerHTML += `
                <tr>

                    <td>
                        ${record.student.name}
                    </td>

                    <td>
                        ${record.student.rollNumber}
                    </td>

                    <td>
                        ${record.subject.subjectName}
                    </td>

                    <td>
                        ${record.attendanceDate}
                    </td>

                    <td>
                        <span class="status-badge ${statusClass}">
                            ${statusText}
                        </span>
                    </td>

                </tr>
            `;

        });

    } catch (error) {

        alert("Error loading records: " + error.message);

    }
}


// Load student attendance summary
async function loadSummary() {

    const studentId =
        document.getElementById("studentId").value;

    const subjectId =
        document.getElementById("summarySubjectId").value;


    if (!studentId || !subjectId) {

        alert("Please enter student ID and subject ID.");

        return;
    }


    try {

        const response = await fetch(
            `${API}/attendance/summary?studentId=${studentId}&subjectId=${subjectId}`
        );


        if (!response.ok) {
            throw new Error("Unable to load attendance summary");
        }


        const summary =
            await response.json();


        document.getElementById("summaryResult").innerHTML = `

            <div class="summary-card">

                <h3>
                    ${summary.studentName}
                </h3>

                <p>
                    <strong>Roll Number:</strong>
                    ${summary.rollNumber}
                </p>

                <p>
                    <strong>Subject:</strong>
                    ${summary.subjectName}
                </p>

                <p>
                    <strong>Total Classes:</strong>
                    ${summary.totalClasses}
                </p>

                <p>
                    <strong>Present:</strong>
                    ${summary.presentClasses}
                </p>

                <p>
                    <strong>Absent:</strong>
                    ${summary.absentClasses}
                </p>

                <h3>
                    Attendance:
                    ${summary.attendancePercentage}%
                </h3>

            </div>
        `;

    } catch (error) {

        alert("Error loading summary: " + error.message);

    }
}


// Find students with low attendance
async function loadLowAttendance() {

    const subjectId =
        document.getElementById("subjectId").value;

    const threshold =
        document.getElementById("threshold").value;


    try {

        const response = await fetch(
            `${API}/attendance/low-attendance/${subjectId}?threshold=${threshold}`
        );


        if (!response.ok) {
            throw new Error(
                "Unable to load low attendance students"
            );
        }


        const students =
            await response.json();


        const result =
            document.getElementById("lowAttendanceResult");


        if (students.length === 0) {

            result.innerHTML = `
                <div class="summary-card">
                    <p>No low attendance students found.</p>
                </div>
            `;

            return;
        }


        result.innerHTML = "";


        students.forEach(student => {

            result.innerHTML += `

                <div class="summary-card">

                    <h3>
                        ${student.studentName}
                    </h3>

                    <p>
                        <strong>Roll Number:</strong>
                        ${student.rollNumber}
                    </p>

                    <p>
                        <strong>Subject:</strong>
                        ${student.subjectName}
                    </p>

                    <p>
                        <strong>Attendance:</strong>
                        ${student.attendancePercentage}%
                    </p>

                </div>
            `;

        });

    } catch (error) {

        alert(
            "Error loading low attendance: " +
            error.message
        );

    }
}


// Load dashboard statistics
async function loadDashboard() {

    try {

        const subjectId =
            document.getElementById("subjectId").value || 1;


        const [studentsResponse, attendanceResponse] =
            await Promise.all([

                fetch(`${API}/students`),

                fetch(`${API}/attendance/subject/${subjectId}`)

            ]);


        if (!studentsResponse.ok ||
            !attendanceResponse.ok) {

            throw new Error(
                "Unable to load dashboard data"
            );
        }


        const students =
            await studentsResponse.json();

        const records =
            await attendanceResponse.json();


        const present =
            records.filter(
                record => record.status === "PRESENT"
            ).length;


        const absent =
            records.filter(
                record => record.status === "ABSENT"
            ).length;


        document.getElementById("totalStudents").textContent =
            students.length;

        document.getElementById("totalRecords").textContent =
            records.length;

        document.getElementById("presentCount").textContent =
            present;

        document.getElementById("absentCount").textContent =
            absent;

    } catch (error) {

        console.error(
            "Dashboard error:",
            error.message
        );

    }
}


// Run when the page is opened
document.addEventListener("DOMContentLoaded", function () {

    setTodayDate();

    loadDashboard();

});
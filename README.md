# MySlither
A Java implementation of the [slither.io](https://slither.io) client, amended for an SCC210 group project.

### Features / Screenshots
![Screenshot01](https://cloud.githubusercontent.com/assets/11258252/15582289/741d9dbe-2370-11e6-82a8-2dc135f823b6.png)

### Prerequisites

- Java 8-11 (11+ works but is unsupported)
- Gradle 7 or later

### Compatibility notes
On some systems, the dark theme causes display issues. Please run the program with the arguments 'no-dark' if this occurs.

### Import into IntelliJ IDEA / Usage

- Open the Gradle project
- Enable "Use auto-import"
- Select "Use local gradle distribution"

Build the jar with `gradle clean shadowJar` and run that jar, or use `gradle run`.

### License
This project is released under the GNU/GPLv3 License. See [LICENSE](LICENSE) for details.

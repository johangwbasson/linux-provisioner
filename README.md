# Linux System Provisioner

This Kotlin application automates the provisioning of Linux systems (Debian, Ubuntu, Fedora) by generating installation commands from a JSON configuration file.

## Features

- Automatic Linux distribution detection
- System configuration (hostname, timezone)
- Package repository management
- Package installation
- Flatpak application installation
- User creation and configuration
- Developer environment setup (nvm, sdkman, ohmyzsh, starship)
- Pre and post installation scripts

## Requirements

- JDK 21 or later
- Gradle 7.0 or later

## Building the Project

```bash
./gradlew build
```

This will create a fat JAR in the `build/libs/` directory.

## Usage

1. Create a configuration file (see `sample-config.json`)
2. Run the application:

```bash
java -jar build/libs/linux-provisioner-1.0-SNAPSHOT.jar sample-config.json
```

The application will:
1. Load the configuration
2. Detect the Linux distribution
3. Generate the appropriate commands
4. Save them to `provision.sh`

You can then review and execute the generated script:

```bash
# Review the script
cat provision.sh

# Execute the script
sudo ./provision.sh
```

## Configuration Format

The configuration is in JSON format with the following structure:

```json
{
  "system": {
    "hostname": "dev-machine",
    "timezone": "America/New_York"
  },
  "repositories": [
    {
      "name": "repo-name",
      "type": "apt",
      "url": "repository-url",
      "key_url": "key-url",
      "distributions": ["debian", "ubuntu"]
    }
  ],
  "packages": [
    "package1",
    "package2"
  ],
  "flatpak": [
    "org.example.App1",
    "org.example.App2"
  ],
  "scripts": {
    "pre_install": "pre-setup.sh",
    "post_install": "post-setup.sh"
  },
  "users": [
    {
      "name": "username",
      "groups": ["group1", "group2"],
      "shell": "zsh",
      "setup": [
        {
          "name": "tool-name",
          "command": "installation-command",
          "post_command": "configuration-command"
        }
      ]
    }
  ]
}
```

## Extending the Tool

### Adding Support for More Distributions

Modify the `detectDistribution` method in `LinuxProvisioner.kt` to recognize additional distributions, and update the command generation methods to handle those distributions.

### Adding New Features

To add support for more configuration options:

1. Update the data classes to include the new options
2. Add a new method to generate commands for the new feature
3. Call this method from the `generateCommands` method

## License

GNU General Public License v3.0
```
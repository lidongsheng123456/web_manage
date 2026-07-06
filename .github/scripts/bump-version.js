// 发版辅助脚本 - 更新所有版本号 (UTF-8 safe)
const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '../..');
const type = process.argv[2]; // patch | minor | major | x.y.z

// 读取当前版本
const pkg = JSON.parse(fs.readFileSync(path.join(root, 'web_ui/package.json'), 'utf8'));
const oldVer = pkg.version;
const [major, minor, patch] = oldVer.split('.').map(Number);

let newVer;
switch (type) {
  case 'patch': newVer = `${major}.${minor}.${patch + 1}`; break;
  case 'minor': newVer = `${major}.${minor + 1}.0`; break;
  case 'major': newVer = `${major + 1}.0.0`; break;
  default: newVer = type;
}

console.log(`  ${oldVer} -> ${newVer}`);

// 更新 package.json
pkg.version = newVer;
fs.writeFileSync(path.join(root, 'web_ui/package.json'), JSON.stringify(pkg, null, 2) + '\n', 'utf8');
console.log('  [OK] web_ui/package.json');

// 更新所有 pom.xml
const poms = ['pom.xml', 'web_admin/pom.xml', 'web_common/pom.xml', 'web_framework/pom.xml', 'web_system/pom.xml'];
poms.forEach(p => {
  const fp = path.join(root, p);
  let content = fs.readFileSync(fp, 'utf8');
  content = content.replace(`<version>${oldVer}</version>`, `<version>${newVer}</version>`);
  fs.writeFileSync(fp, content, 'utf8');
  console.log(`  [OK] ${p}`);
});

console.log(`\n  Done! New version: ${newVer}`);

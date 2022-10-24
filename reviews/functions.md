# 功能实现 review 结果

## Summary

### Level-1

1. 🟢 Scenario: 初始化后，任务列表默认为空

1. 🟢 Scenario: 添加 3 个任务并可以正常显示

### Level-2

1. 🟢 Scenario: 可以添加 10 个及以上的任务并且列表格式正确

1. 🟢 Scenario: mark TBD 里的第一个任务

1. 🟢 Scenario: mark TBD 里的多个任务后可以再继续正常 add 新 task

1. 🟢 Scenario: mark TBD 里的所有任务

1. 🟢 Scenario: 一次删除一个 task，且后续的 add task 操作能够正常进行

1. 🟢 Scenario: 一次删除多个 task，且后续的 add task 操作能够正常进行

1. 🟢 Scenario: 一次删除全部 task，且后续的 add task 操作能够正常进行

### Level-3

1. 🟢 Scenario: 执行任何 child command 之前必须先执行 init

1. 🟢 Scenario: 可以添加标题包含空格及其它符号的 task

1. 🟢 Scenario: 如果删除任务时没有提供任何 ID，则不做任何改动

1. 🟢 Scenario: 如果删除任务时指定的 ID 不存在，则不做任何改动

1. 🟢 Scenario: mark 任务时没有提供任何 ID

1. 🟢 Scenario: 同时 mark 多个已 completed、不存在及已删除的任务

## Details

### Level-1

#### 🟢 Scenario: 初始化后，任务列表默认为空

Steps:

```bash
rm -rf ~/.todo
todo init
todo list
```

#### 🟢 Scenario: 添加 3 个任务并可以正常显示

Steps:

```bash
rm -rf ~/.todo
todo init
todo add Task 001
todo add Task 002
todo add Task 003
todo list
```

### Level-2

#### 🟢 Scenario: 可以添加 10 个及以上的任务并且列表格式正确

Steps:

```bash
rm -rf ~/.todo
todo init
for id in $(seq -w 12)
do
    todo add Task "${id}"
done
todo list
```

#### 🟢 Scenario: mark TBD 里的第一个任务

Steps:

```bash
rm -rf ~/.todo
todo init
todo add Task 01
todo add Task 02
todo add Task 03
todo list
todo mark 1
todo list
```

#### 🟢 Scenario: mark TBD 里的多个任务后可以再继续正常 add 新 task

Steps:

```bash
rm -rf ~/.todo
todo init
todo add Task 01
todo add Task 02
todo add Task 03
todo add Task 04
todo add Task 05
todo list
todo mark 1 3 5
todo list
todo add Task 06
todo list
```

#### 🟢 Scenario: mark TBD 里的所有任务

Steps:

```bash
rm -rf ~/.todo
todo init
todo add Task 01
todo add Task 02
todo add Task 03
todo list
todo mark 1 2 3
todo list
```

#### 🟢 Scenario: 一次删除一个 task，且后续的 add task 操作能够正常进行

Steps:

```bash
rm -rf ~/.todo
todo init
todo add Task 01
todo add Task 02
todo add Task 03
todo add Task 04
todo add Task 05
todo add Task 06
todo mark 4 5 6
todo list
todo remove 1
todo remove 3
todo remove 4
todo remove 6
todo list
todo add Task 07
todo list
```

#### 🟢 Scenario: 一次删除多个 task，且后续的 add task 操作能够正常进行

Steps:

```bash
rm -rf ~/.todo
todo init
for id in $(seq -w 10); do
  todo add Task "${id}"
done
todo mark {6..10}
todo list
todo remove 1 5 3 6 10 8
todo list
todo add Task 11
todo list
```

#### 🟢 Scenario: 一次删除全部 task，且后续的 add task 操作能够正常进行

Steps:

```bash
rm -rf ~/.todo
todo init
for id in $(seq -w 6); do
  todo add Task "${id}"
done
todo mark {4..6}
todo list
todo remove {1..6}
todo list
todo add Task 7
todo list
```

### Level-3

#### 🟢 Scenario: 执行任何 child command 之前必须先执行 init

Steps:

```bash
rm -rf ~/.todo
todo list 2>&1
todo add 2>&1
todo remove 2>&1
todo mark 2>&1
```

#### 🟢 Scenario: 可以添加标题包含空格及其它符号的 task

Steps:

```bash
rm -rf ~/.todo
todo init
todo list
todo add 
todo add
todo add foo bar
todo add "foo bar"
todo add "foo   bar"
todo add "   foo   bar   "
todo add 9
todo add 10
todo add 书山有路勤为径！
todo add '#@!$%^&*()|<>?'
todo list
todo mark 7 8
todo list
todo mark {1..6} 9 10
todo list
todo remove 7 8
todo list
todo remove {1..10}
todo list
```

#### 🟢 Scenario: 如果删除任务时没有提供任何 ID，则不做任何改动

Steps:

```bash
rm -rf ~/.todo
todo init
todo add foo
todo add bar
todo mark 2
todo list
todo remove
todo list
```

#### 🟢 Scenario: 如果删除任务时指定的 ID 不存在，则不做任何改动

Steps:

```bash
rm -rf ~/.todo
todo init
todo add foo
todo add bar
todo mark 2
todo list
todo remove 404
todo remove foo
todo remove -2
todo list
```

#### 🟢 Scenario: mark 任务时没有提供任何 ID

Steps:

```bash
rm -rf ~/.todo
todo init
todo add foo
todo add bar
todo mark 2
todo list
todo mark
todo list
```

#### 🟢 Scenario: 同时 mark 多个已 completed、不存在及已删除的任务

Steps:

```bash
rm -rf ~/.todo
todo init
todo add foo
todo add bar
todo add foobar
todo list
todo mark 2
todo remove 3
[[ -e ~/.todo/tasks ]] && cp ~/.todo/tasks /tmp
todo list
todo mark 3 2 404
todo list
[[ -e /tmp/tasks ]] && diff --brief /tmp/tasks ~/.todo/tasks
```

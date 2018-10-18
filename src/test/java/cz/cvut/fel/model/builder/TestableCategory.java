package cz.cvut.fel.model.builder;

import cz.cvut.fel.model.Category;

public class TestableCategory extends Category {

    public static class Builder {
        private TestableCategory categoryToBuild;

        public Builder() {
            categoryToBuild = new TestableCategory();
        }

        public TestableCategory build() {
            TestableCategory builtPerson = categoryToBuild;
            categoryToBuild = new TestableCategory();

            return builtPerson;
        }

        public Builder setId(Long id) {
            this.categoryToBuild.setId(id);
            return this;
        }

        public Builder setName(String name) {
            this.categoryToBuild.setName(name);
            return this;
        }

        public Builder setParent(Category parent) {
            this.categoryToBuild.setParent(parent);
            return this;
        }
    }
}
